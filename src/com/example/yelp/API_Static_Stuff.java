package com.example.yelp;

/**
 * This class is used primarily for API keys and secrets to create a central repository.
 * This is useful for having one place to manage several API keys.
 * 
 * @author ograycoding.wordpress.com
 */
public class API_Static_Stuff {
     
    private final String YELP_CONSUMER_KEY ="zyxlkHBTSeCnGNmE7yb9Xg";
    private final String YELP_CONSUMER_SECRET = "hfQ15skXXT--_rA2MwXwb05JBh0";
    private final String YELP_TOKEN = "6_NvTqIl0YdUKfryc21lnod4oxMzkZLt";
    private final String YELP_TOKEN_SECRET = "TleZfV3stIr6QGbcOg1Fu9fQjkM";
     
     
    public String getYelpConsumerKey(){
        return YELP_CONSUMER_KEY;
    }
     
    public String getYelpConsumerSecret(){
        return YELP_CONSUMER_SECRET;
    }
     
    public String getYelpToken(){
        return YELP_TOKEN;
    }
     
    public String getYelpTokenSecret(){
        return YELP_TOKEN_SECRET;
    }
 
}