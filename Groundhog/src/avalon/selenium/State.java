/**
 * 
 */
package avalon.selenium;

/**
 * @author scarleton3
 *
 */
public enum State implements StateventListener {
	initialState {
		@Override public boolean onEventTextValue(String value) {
			if(value.equals("Matching Documents"))
				return true;
			return false;
		}
	},
	MatchDocState {
		@Override public boolean onEventTextValue(String value) {
			if(value.equals("Matching Specs")||value.equals("Matching Plans"))
				return true;
			return false;
		}
	},
	MatchSpecState {
		@Override public boolean onEventTextValue(String value) {
			if(value.length()>=1)
				return true;
			return false;
		}
	},
	SpecDocState {
		@Override public boolean onEventTextValue(String value) {
			return false;
		}
	};
	private static State[] vals = values();

	public State next(){
		return vals[(this.ordinal()+1) % vals.length];    
	}
}
