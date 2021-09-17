/*

Implementation of a dewitters game loop, code based on the site
    https://dewitters.com/dewitters-gameloop/

using System.currentTimeMillis(), so don't change your computer clock
    while this runs

*/
package Game_of_Life_Modified;

import Game_of_Life_Modified.Displayer;

//**************************************************************
//**************************************************************
public class GameLoop
{
    int ticks_per_second = 25;
    int skip_ticks = 1000 / ticks_per_second;
    int max_frameskip = 5;


    //**************************************************************
    //Constructor to use the default values for the class properties
    public GameLoop()
    {
    }

    //**************************************************************
    //Constructor to use custom values for the class parameters
    public GameLoop(int tps, int st, int mf)
    {
        ticks_per_second = tps;
        skip_ticks = st;
        max_frameskip = mf;
    }

    //**************************************************************
    public void runLoop(Updater _Updater, Displayer _Displayer)
    {
        double next_game_tick = System.currentTimeMillis();
        int loops;
        float interpolation;

        boolean game_is_running = true;
        while(game_is_running)
        {
            loops = 0;
            double nowTime = System.currentTimeMillis();
            while(nowTime > next_game_tick && loops < max_frameskip)
            {
                _Updater.Update(nowTime);

                next_game_tick += skip_ticks;
                loops++;
            }

            interpolation = (float)(System.currentTimeMillis() + skip_ticks - next_game_tick) / (float)(skip_ticks);
            _Displayer.Display(interpolation);
        }

        return;
    }
}
