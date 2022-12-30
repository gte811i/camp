package avalon.cmd;
///**
// * 
// */
//
///**
// * @author Shane
// *
// */
//public class CMDStateMachine {
//	private State[] states     = { new InvalidProject(), new MatchDoc(), new MatchSpec(), new SpecDoc() }; // 2. states
//	private int[][] transition = { {1,0},{2,0}, {3,2}, {1,2} }; 
//	private int     current    = 0;                             
//	private void next( int msg ) { current = transition[current][msg]; }
//
//	public void valid()  { states[current].valid();   next( 0 ); }
//	public void invalid() { states[current].invalid();  next( 1 ); }
//}
//
//abstract class State {
//	public void valid()  { System.out.println( "error" ); }
//	public void invalid() { System.out.println( "error" ); }  
//}
//
//class MatchDoc extends State {
//	public void valid()  { System.out.println( "Found Matching Document Section" ); }
//	public void invalid() { System.out.println( "No Matching Document Section" ); }
//}
//
//class MatchSpec extends State {
//	public void valid()  { System.out.println( "Found Matching Spec." ); }
//	public void invalid() { System.out.println( "No Matching Spec." ); }
//}
//
//class SpecDoc extends State {
//	public void valid()  { System.out.println( "Spec Document" ); }
//	public void invalid()  { System.out.println( "No Spec Document" ); }
//}
//
//class InvalidProject extends State {
//}
//
