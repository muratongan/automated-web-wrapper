
package automatedwebwrapper.WebCrawler.Interfaces;

/**
 * M. H. Nassabi
 * @author s098828
 */
public interface InterfaceThreadMsgReceiver {

      /* A message is received by thread manager
       * which was initially sent by a thread with Id threadId
       */
        public void msgReceived(Object theMessage, int threadId);

      /*  the thread manager sends this message when
       *  one of its threads finishes processing
       */
        public void threadFinishedProcessing(int threadId);

       /*  the thread manager informs the class implementing this interface
        * that all of its threads have finished processing
       */
       public void allThreadsFinishedProcessing();

}
