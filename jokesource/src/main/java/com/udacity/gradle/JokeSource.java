package com.udacity.gradle;

import java.util.Random;

public class JokeSource {
    String [] jokeSet= {"Light travels faster than sound. This is why some people appear bright until you hear them speak",
            "If i had a dollar for every girl that found me unattractive, they would eventually find me attractive",
            "I needed a password eight characters long so I picked Snow White and the Seven Dwarfs",
            "Today a man knocked on my door and asked for a small donation towards the local swimming pool. I gave him a glass of water"};
    public JokeSource(){

    }
    public String getJoke(){
        Random random = new Random();
        int select = random.nextInt(jokeSet.length);
        return jokeSet[select];
    }


}
