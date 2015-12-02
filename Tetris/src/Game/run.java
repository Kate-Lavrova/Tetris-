package Game;


/**
 * Created by SONY on 15.06.2015.
 */
public class run  extends Replay {


    /**
     * Constructor. Is used to initialise fields in the class
     *
     */

    /**
     * @param monitor
     */


    public void run(Object monitor) {
		/*this.monitor = monitor;*/


        while (true) {
            synchronized (monitor) {

                try {
                    // if(!checking)
                    monitor.wait();
                    checkGameEvents();
                } catch (Throwable error) {
                    error.printStackTrace();
                }
                // checking = false;
                monitor.notifyAll();
            }
        }
    }

    public void checkGameEvents() throws Throwable {

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {

                }
            }


        for (int i = 0, y = 10; i < 20; i++, y += 30)
            for (int j = 0, x = 14; j < 10; j++, x += 30) {
                Playing_Field_cell playingFieldCell = new Playing_Field_cell();
                playingFieldCell.returnGameFieldCell().setPrefSize(30, 30);
                playingFieldCell.returnGameFieldCell().setLayoutX(x);
                playingFieldCell.returnGameFieldCell().setLayoutY(y);

            }
        for (int i = 0, y = 269; i < 2; i++, y += 30)
            for (int j = 0, x = 391; j < 4; j++, x += 30) {
                Playing_Field_cell playingFieldCell = new Playing_Field_cell();
                playingFieldCell.returnGameFieldCell().setPrefSize(30, 30);
                playingFieldCell.returnGameFieldCell().setLayoutX(x);
                playingFieldCell.returnGameFieldCell().setLayoutY(y);

            }






    }
}
