package fil.coo.actions.interfaces;

import fil.coo.actions.ActionState;
import fil.coo.exception.ActionFinishedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class ActionTest {

    protected abstract Action createAction();

    protected Action action;

    @Before
    public void setupAction() {
        this.action = this.createAction();
    }

    @Test
    public void stateIsReadyWhenCreated() {
        assertEquals(this.action.getState(), ActionState.READY);
    }

    @Test
    public void isFinishedOnlyWhenStopConditionIsReached() throws ActionFinishedException {
        assertFalse(this.action.isFinished());
        assertFalse(this.action.stopCondition());

        while (!this.action.stopCondition()) {
            assertFalse(this.action.isFinished());
            this.action.doStep();
        }

        assertTrue(this.action.isFinished());
    }

    @Test(expected = ActionFinishedException.class)
    public void doStepThrowsExceptionOnlyWhenIsFinished() throws ActionFinishedException {
        // On déroule l'action et si exception, le test échoue
        while (!this.action.isFinished()) {
            try {
                this.action.doStep();
            } catch (ActionFinishedException e) {
                fail();
            }
        }

        assertTrue(this.action.isFinished());
        // Lance l'exception
        this.action.doStep();
    }

}
