package avalon.selenium;
//new InvalidProject(), new MatchDoc(), new MatchSpec(), new SpecDoc()
interface StateventListener {
	boolean onEventTextValue(String value);
	//	boolean onEventMatchSpec(String value);
	//	boolean onEventSpecDoc(String value);
}


public class StateMachine implements StateventListener {
	public State currentState = State.initialState;

	@Override public boolean onEventTextValue(String value) {
		if(currentState.onEventTextValue(value))
		{
			currentState = currentState.next();
			return true;
		}
		return false;
	}
}