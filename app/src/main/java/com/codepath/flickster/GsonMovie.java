package com.codepath.flickster;

import java.util.ArrayList;

/**
 * Created by YichuChen on 2/16/17.
 */

public class GsonMovie {

        public int page;
        public ArrayList<Results> results = new ArrayList<>();

        public class Results {
            public String poster_path;
            public String backdrop_path;
            public String title;
            public String overview;
            public float vote_average;
            public String release_date;
            public String id;
        }
}

