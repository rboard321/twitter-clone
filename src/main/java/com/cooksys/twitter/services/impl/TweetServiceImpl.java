package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.dtos.*;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.exceptions.*;
import com.cooksys.twitter.mappers.*;
import com.cooksys.twitter.repositories.*;
import com.cooksys.twitter.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Class: TweetServiceImpl.java
 * Authors: Jordan Adkins, Ricky Board, Emmanuel Ekamby
 * Date: 8/30/22
 * About: This is the service that handles all endpoints /tweets related and possibly a bit more
 * depending on where we want to put validation.
 */

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final CredentialsMapper credentialsMapper;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final UserMapper userMapper;


    /*========================================================================================*\
    +⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯ GET ENDPOINTS ⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯⌯+
    \*========================================================================================*/
    @Override
    public List<TweetResponseDto> getTweets() {
        List<Tweet> tweetList = tweetRepository.findAll();
        tweetList.removeIf(Tweet::isDeleted);
        tweetList.sort(Comparator.comparing(Tweet::getPosted).reversed());
        setTweetAuthorUsername(tweetList);
        return tweetMapper.entitiesToDtos(tweetList);
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        setTweetAuthorUsername(tweet);
        return tweetMapper.entityToDto(tweet);
    }


    @Override
    public List<HashtagDto> getTweetsTags(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        List<Hashtag> hashtagList = tweet.getHashtags();
        return hashtagMapper.entitiesToDtos(tweet.getHashtags());
    }

    @Override
    public List<UserResponseDto> getTweetsLikes(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        List<User> userList = tweet.getLikes();
        userList.removeIf(User::isDeleted);
        return userMapper.entitiesToDtos(userList);
    }

    @Override
    public ContextDto getTweetContext(Long id, ContextDto contextDto) {
        // Error and tweet body format checking
        Tweet tweet = checkIfValidIdThenGet(id);

        // Get before tweet list, remove deleted tweets and sort
        List<Tweet> beforeTweetList = setContextBefore(tweet);
        beforeTweetList.removeIf(Tweet::isDeleted);
        beforeTweetList.sort(Comparator.comparing(Tweet::getPosted));

        // Get after tweet list, remove deleted tweets and sort
        List<Tweet> afterTweetList = setContextAfter(tweet);
        afterTweetList.removeIf(Tweet::isDeleted);
        afterTweetList.sort(Comparator.comparing(Tweet::getPosted));

        // Set the contextDto
        
        setTweetAuthorUsername(tweet);
        contextDto.setTarget(tweet);
        contextDto.setBefore(beforeTweetList);
        contextDto.setAfter(afterTweetList);

        return contextDto;
    }

    @Override
    public List<TweetResponseDto> getTweetReplies(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        List<Tweet> tweetReplies = tweet.getReplies();
        List<Tweet> returnTweetReplies = new ArrayList<>();
        for (Tweet t : tweetReplies) {
            if (!t.isDeleted()) {
                returnTweetReplies.add(t);
                setTweetAuthorUsername(t);
            }

        }
        return tweetMapper.entitiesToDtos(returnTweetReplies);
    }

    @Override
    public List<TweetResponseDto> getTweetReposts(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        List<Tweet> tweetReposts = tweet.getReposts();
        List<Tweet> returnTweetReposts = new ArrayList<>();
        for (Tweet t : tweetReposts) {
            if (!t.isDeleted()) {
                returnTweetReposts.add(t);
                setTweetAuthorUsername(t);
            }
        }
        return tweetMapper.entitiesToDtos(returnTweetReposts);
    }

    @Override
    public List<UserResponseDto> getTweetMentions(Long id) {
        Tweet tweet = checkIfValidIdThenGet(id);
        List<User> tweetMentions = tweet.getUserMentioned();
        List<User> returnUserMentions = new ArrayList<>();
        for (User u : tweetMentions) {
            if (!u.isDeleted())
                returnUserMentions.add(u);
        }
        return userMapper.entitiesToDtos(returnUserMentions);
    }


    /*=========================================================================================*\
    |_\____/\____/\____/\____/\____/\____/\ ⏜⏝⏜⏝⏜⏝⏜⏝⏜/\____/\____/\____/\____/\____/\____/|
    | ~\~~/~~\~~/~~\~~/~~\~~/~~\~~/~~\~~/=+ POST ENDPOINTS +=\~~/~~\~~/~~\~~/~~\~~/~~\~~/~~\~~/~|
    |___\/____\/____\/____\/____\/____\/_⏝⏜⏝⏜⏝⏜⏜⏝⏜⏝⏜⏝_\/____\/____\/____\/____\/____\/__|
    \*=========================================================================================*/

    @Override
    public TweetResponseDto postTweet(TweetRequestDto tweetRequestDto) {
        // Error and proper formatting checks
        checkTweetRequestBody(tweetRequestDto);
        Tweet tweet;
        Optional<User> optionalUser = userRepository.findByCredentials(tweetRequestDto.getCredentials());
        User databaseAuthor;
        databaseAuthor = optionalUser.orElseGet(optionalUser::get);
        runUserExistsTests(databaseAuthor);

        // Initialize new tweet
        initTweet(tweetRequestDto, databaseAuthor);

        // Save tweet to db and update other entities as needed
        tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
        databaseAuthor.getTweets().add(tweetRepository.saveAndFlush(tweet));
        userRepository.saveAndFlush(databaseAuthor);

        // Add hashtags and mentions
        searchAndAddHashtags(tweetRequestDto, tweet);
        searchAndAddMentions(tweetRequestDto, tweet);

        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public TweetResponseDto postTweetReply(Long id, TweetRequestDto tweetRequestDto) {
        // Error and proper formatting checks
        checkTweetRequestBody(tweetRequestDto);
        Tweet tweetReply;
        Tweet tweetOriginal = checkIfValidIdThenGet(id);
        User user = userRepository.getByCredentials_UsernameAndCredentials_Password(tweetRequestDto.getCredentials().getUsername(), tweetRequestDto.getCredentials().getPassword());
        runUserExistsTests(user);

        // Initializing new tweet
        initTweet(tweetRequestDto, user);
        tweetRequestDto.setInReplyTo(tweetOriginal);

        // Save tweet to db and update other entities as needed
        tweetReply = tweetMapper.requestDtoToEntity(tweetRequestDto);
        user.getTweets().add(tweetRepository.saveAndFlush(tweetReply));
        tweetOriginal.getReplies().add(tweetReply);
        tweetRepository.saveAndFlush(tweetOriginal);

        // Add hashtags and mentions
        searchAndAddHashtags(tweetRequestDto, tweetReply);
        searchAndAddMentions(tweetRequestDto, tweetReply);

        return tweetMapper.entityToDto(tweetReply);
    }

    @Override
    public TweetResponseDto postTweetRepost(Long id, CredentialsDto credentialsDto, TweetRequestDto tweetRepost) {
        // Error and proper formatting checks
        Tweet tweetOriginal = checkIfValidIdThenGet(id); // This is the tweet we are reposting
        Optional<User> user = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));
        if (user.isPresent())
            runUserExistsTests(user.get());
        else
            throw new NotFoundException("Could not find user. Username or password may be wrong.");

        // Init new tweet
        //TweetRequestDto tweetRequestDto = tweetMapper.entityToRequestDto(tweetOriginal);
        tweetRepost.setCredentials(credentialsMapper.DtoToEntity(credentialsDto));
        initTweet(tweetRepost, user.get());

        // Save tweet to db and update other entities as needed // This is the new tweet
        tweetRepost.setRepostOf(tweetOriginal);
        //saves tweet repost to the database
        tweetRepository.saveAndFlush(tweetMapper.requestDtoToEntity(tweetRepost));
        //gets the user and gets the list of tweets from that user then adds the tweetrepost to that user
        user.get().getTweets().add(tweetRepository.saveAndFlush(tweetMapper.requestDtoToEntity(tweetRepost)));
        //gets a list of reposts of the original tweet and adds the tweetRepost
        tweetOriginal.getReposts().add(tweetMapper.requestDtoToEntity(tweetRepost));
        //save the updated original tweet data to the repository
        tweetRepository.saveAndFlush(tweetOriginal);

        return tweetMapper.entityToDto(tweetMapper.requestDtoToEntity(tweetRepost));
    }

    @Override
    public void postTweetLike(Long id, CredentialsDto credentialsDto) {
        // Error and proper formatting checks
        Tweet tweet = checkIfValidIdThenGet(id);
        User user = userRepository.getByCredentials_UsernameAndCredentials_Password(credentialsMapper.DtoToEntity(credentialsDto).getUsername(), credentialsMapper.DtoToEntity(credentialsDto).getPassword());
        runUserExistsTests(user);

        if (!tweet.getLikes().contains(user)) {
            // Add user to list of tweet likers
            tweet.getLikes().add(user);
            // Add tweet to list of tweets user has liked
            user.getLikedTweets().add(tweet);
        }else{
            return;
        }

        // Save and flush
        tweetRepository.saveAndFlush(tweet);
        userRepository.saveAndFlush(user);
    }


    /*==========================================================================================*\
    | ⏝⏜⏝⏜⏝⏜⏝⏜ ⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜ DELETE ENDPOINTS ⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜⏝⏜ |
    \*==========================================================================================*/

    @Override
    public TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto) {
        if (credentialsDto == null)
            throw new BadRequestException("Request body must contain valid credentials.");

        Tweet tweet = checkIfValidIdThenGet(id);
        tweet.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
        // Check credentials to see if they are equal
        if (tweet.getAuthor().getCredentials().equals(credentialsMapper.DtoToEntity(credentialsDto))) {
            tweet.setDeleted(true);
            tweetRepository.saveAndFlush(tweet);
        } else
            throw new NotAuthorizedException("Error: Username or password is incorrect.");

        return tweetMapper.entityToDto(tweet);
    }



    /*============================================================================================*\
    |-/|--/|--/|--/|--/|--/|--/|--/|--/|--/|⏜⏝⏜⏝⏜⏝⏜⏝⏜ |\  |\  |\  |\  |\  |\  |\  |\  |\  |\ |
    |/~|~/~|~/~|~/~|~/~|~/~|~/~|~/~|~/~|~/=+ HELPER METHODS +=\=|~\~|~\~|~\~|~\~|~\~|~\~|~\~|~\~|~\|
    |--|/--|/--|/--|/--|/--|/--|/--|/--|/-⏝⏜⏝⏜⏝⏜⏜⏝⏜⏝⏜⏝ \|  \|  \|  \|  \|  \|  \|  \|  \|  |
    \*===========================================================================================*/

    /*
        The searchAndAddMentions is a helper method whose only purpose is to parse tweet content
        and add the appropriate @{username} to the tweets mentions list.
        KNOWN FAULTS: When a user mention occurs in-step with punctuation there is no way to handle
        removing the punctuation to grab the mention at the moment.
     */
    private void searchAndAddMentions(TweetRequestDto tweetRequestDto, Tweet tweet) {
        // Since splitting by # we may accidentally remove a leading hashtag so want to check for that
        boolean firstIsAdded = (tweetRequestDto.getContent().charAt(0) == '@');

        // List of POSSIBLE hashtags
        String[] possibleMentions = tweetRequestDto.getContent().split("@");
        List<String> actualMentions = new ArrayList<>();
        int count = 0;

        // Get list of ACTUAL mentions
        for (String string : possibleMentions) {
            if (firstIsAdded || count > 0) {
                if (string.split(" ").length > 0)
                    // This is for splits with multiple words. I,e: "#is cool" -> ["is cool] -> ["is","cool"] -> "is" is added
                    actualMentions.add(string.split(" ")[0]);
                else
                    // This is if it's split into just one word: "#Hello" -> ["Hello"] -> "Hello" is added
                    actualMentions.add(string);
            }
            count++;
        }

        // Verify that users exist
        for (String mentions : actualMentions) {
            //Check if username does not exist or is deleted

            Optional<User> user = userRepository.findByCredentials_Username(mentions);
            if (user.isEmpty() || user.get().isDeleted())
                throw new NotFoundException("Error: User mentioned in tweet not found or deleted.");
            else{
                tweet.getUserMentioned().add(user.get());


                user.get().getMentions().add(tweet);
                // Save the updated user that was mentioned to the repository; don't need to save the tweetRequestDto because that happens in postTweet()
                userRepository.saveAndFlush(user.get());
            }
        }
    }

    /*
        The searchAndAddHashtags is a helper method whose only purpose is to parse tweet content
        and add the appropriate #{text} to the tweets hashtags list.
        KNOWN FAULTS: When a user hashtag occurs in-step with punctuation there is no way to handle
        removing the punctuation to grab the hashtag at the moment.
     */
    private void searchAndAddHashtags(TweetRequestDto tweetRequestDto, Tweet tweet) {
        // Since splitting by # we may accidentally remove a leading hashtag so want to check for that
        boolean firstIsAdded = (tweetRequestDto.getContent().charAt(0) == '#');

        // List of POSSIBLE hashtags
        String[] possibleHashtags = tweetRequestDto.getContent().split("#");
        List<String> actualHashtags = new ArrayList<>();
        int count = 0;

        // Get list of ACTUAL hashtags
        for (String string : possibleHashtags) {
            if (firstIsAdded || count > 0) {
                if (string.split(" ").length > 0)
                    // This is for splits with multiple words. I,e: "#is cool" -> ["is cool] -> ["is","cool"] -> "is" is added
                    actualHashtags.add(string.split(" ")[0]);
                else
                    // This is if it's split into just one word: "#Hello" -> ["Hello"] -> "Hello" is added
                    actualHashtags.add(string);
            }
            count++;
        }

        // SEARCH FOR EXISTING HASHTAGS IF NONE FOUND MAKE A NEW ONE
        Hashtag hashtag;
        for (String string : actualHashtags) {
            hashtag = hashtagRepository.getByLabel(string);
            if (hashtag == null) {
                hashtag = new Hashtag();
                hashtag.setTweets(new ArrayList<>());
                hashtag.setLabel(string);
            } else {
                hashtag = hashtagRepository.getByLabel(string);
                hashtagRepository.getByLabel(string).setLabel(""); // Doing this weird setLabel("") then setLabel(string) to get the hashtag timestamp to update 'automatically'
                hashtagRepository.saveAndFlush(hashtag);
                hashtagRepository.getByLabel("").setLabel(string);
            }
            hashtag.getTweets().add(tweet);
            tweet.getHashtags().add(hashtag);
            hashtagRepository.saveAndFlush(hashtag);
        }
    }

    // Helper method that just runs some simple tests to see if user exists
    private void runUserExistsTests(User user) {
        if (user == null)
            throw new NotFoundException("Error! Username or password invalid.");
        if (user.isDeleted())
            throw new NotFoundException("Error! User has been deleted.");
    }

    // Helper method that validates a given id then returns the corresponding tweet
    private Tweet checkIfValidIdThenGet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted())
            throw new NotFoundException("Tweet with id:" + id + " was not found.");
        return optionalTweet.get();
    }

    // Helper method that just checks a tweet request body for content
    private void checkTweetRequestBodyForContent(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getContent() == null)
            throw new BadRequestException("The requested body is formatted improperly.");
    }

    // Helper method that initializes new tweets
    private void initTweet(TweetRequestDto tweetRequestDto, User user) {
        user.setUsername(tweetRequestDto.getCredentials().getUsername());
        tweetRequestDto.setInReplyTo(null);
        tweetRequestDto.setRepostOf(null);
        tweetRequestDto.setAuthor(user);
        tweetRequestDto.setDeleted(false);
        tweetRequestDto.setLikes(new ArrayList<>());
        tweetRequestDto.setHashtags(new ArrayList<>());
        tweetRequestDto.setUserMentioned(new ArrayList<>());
        tweetRequestDto.setReplies(new ArrayList<>());
        tweetRequestDto.setReposts(new ArrayList<>());
    }

    // Helper method to check for credentials
    private void checkTweetRequestBodyForCredentials(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getCredentials() == null || tweetRequestDto.getCredentials().getPassword() == null)
            throw new BadRequestException("No credentials found in tweet request.");
        if (tweetRequestDto.getCredentials().getUsername() == null)
            throw new BadRequestException("No credentials found in tweet request.");
    }

    // Helper method to confirm tweet request body is valid
    private void checkTweetRequestBody(TweetRequestDto tweetRequestDto) {
        checkTweetRequestBodyForContent(tweetRequestDto);
        checkTweetRequestBodyForCredentials(tweetRequestDto);
    }

    // Helper method to set before field of context
    private List<Tweet> setContextBefore(Tweet tweet) {
        return setContextBeforeRecurse(tweet.getInReplyTo(), new ArrayList<>());
    }

    // Recursive helper method to setContextBefore
    private List<Tweet> setContextBeforeRecurse(Tweet tweet, ArrayList<Tweet> list) {
        if (tweet == null)
            return list;
        tweet.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
        list.add(tweet);
        return setContextBeforeRecurse(tweet.getInReplyTo(), list);
    }

    // Helper method to set after field of context
    private List<Tweet> setContextAfter(Tweet tweet) {
        return setContextAfterRecurse(tweet, new ArrayList<>());
    }

    // Recursive helper method to setContextAfter
    private List<Tweet> setContextAfterRecurse(Tweet tweet, ArrayList<Tweet> list) {
        if (tweet.getReplies().size() == 0)
            return list;
        for (Tweet t : tweet.getReplies()){
        	System.out.println(">>>>>>> 442 " +t.toString());
            t.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
            list.add(t);
            System.out.println(">>>>>>> 445 " + list.toString());
            return setContextAfterRecurse(t, list);
        }
        return list;
    }

    private void setTweetAuthorUsername(List<Tweet> tweetList){
        for (Tweet t : tweetList){
            t.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
        }
    }

    private void setTweetAuthorUsername(Tweet tweet){
        tweet.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
    }
}
