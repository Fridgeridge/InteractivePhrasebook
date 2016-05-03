package se.chalmers.phrasebook.gui;

import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;

/**
 * An interface for communication between a fragment to an activity
 * Created by David on 2016-04-13.
 */
public interface FragmentCommunicator {


    void updateSyntax(int optionIndex, SyntaxNodeList l, int childIndex, boolean isAdvanced);

    void setPhraseListFragment(String id);

<<<<<<< HEAD
    boolean removePhrasebook(String id);

    void pageChanged();
=======
    void setToTranslationFragment(int id);

    boolean removePhrasebook(String id);
>>>>>>> 46a0c41d3ed615a6c24901d43aa0012ad5e55642

}
