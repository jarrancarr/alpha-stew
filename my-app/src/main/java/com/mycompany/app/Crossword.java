package com.mycompany.app;

import java.util.Map;

/**
 * A Crossword is how an implementing object will be used
 *
 */
public interface Crossword {

    void solve();
    Map<String,String> inspect();
    
}
