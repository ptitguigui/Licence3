package automata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class AutomataUtils {

	/**
	 * Extends automaton a, so that it accepts also this word. Created states
	 * are prefixed by 'q_'
	 *
	 * @param word
	 *            : word to be accepted
	 * @param a
	 *            : target automaton
	 */
	public static void addSingleton(String word, AutomatonBuilder a) {
		addSingleton(word, a, "q");
	}

	/**
	 * Extends automaton a so that it accepts also this word. Created states are
	 * prefixed by namePrefix followed by '_'
	 *
	 * @param word
	 *            : word to be accepted
	 * @param a
	 *            : target automaton
	 * @param namePrefix
	 *            : prefix to use for state names.
	 */
	public static void addSingleton(String word, AutomatonBuilder a, String namePrefix) {
		a.addNewState(namePrefix + "_epsilon");
		for (int i = 0; i < word.length(); i++) {
			a.addNewState(namePrefix + "_" + word.substring(0, i + 1));
		}
		a.setInitial(namePrefix + "_epsilon");
		a.addTransition(namePrefix + "_epsilon", word.charAt(0), namePrefix + "_" + word.substring(0, 1));
		for (int i = 0; i < word.length() - 1; i++) {
			a.addTransition(namePrefix + "_" + word.substring(0, i + 1), word.charAt(i),
					namePrefix + "_" + word.substring(0, i + 2));
		}
		a.setAccepting(namePrefix + "_" + word);
	}

	/**
	 * Extends automaton a so that it accepts also this finite language created
	 * states are prefixed by namePrefix followed by '_'
	 *
	 * @param finiteLanguage
	 *            : set of words to be accepted
	 * @param a
	 *            : target automaton
	 */
	public static void addFiniteSet(Iterable<String> finiteLanguage, AutomatonBuilder a) {
		int i = 0;
		for (String word : finiteLanguage) {
			addSingleton(word, a, "q" + i);
			i++;
		}
	}

	/**
	 * Extends automaton a so that it accepts also language denoted by exp
	 * created states are prefixed by namePrefix followed by '_'
	 *
	 * @param exp
	 *            : flat regular expression (only letters and *)
	 * @param a
	 *            : target automaton
	 */
	public static void addFlatExp(String exp, AutomatonBuilder a) {
		addFlatExp(exp, a, "q");
	}

	/**
	 * Extends automaton a so that it accepts also language denoted by exp
	 * created states are prefixed by namePrefix followed by '_'
	 *
	 * @param exp
	 *            : flat regular expression (only letters and *)
	 * @param a
	 *            : target automaton
	 * @param namePrefix
	 *            : prefix to use for state names.
	 */
	public static void addFlatExp(String exp, AutomatonBuilder a, String namePrefix) {

		a.addNewState(namePrefix + "_epsilon");
		for (int i = 0; i < exp.length() - 1; i++) {
			if (exp.charAt(i) != '*' && exp.charAt(i + 1) != '*') {
				a.addNewState(namePrefix + "_" + exp.substring(0, i + 1));
				System.out.println("new state " + namePrefix + "_" + exp.substring(0, i + 1));
			}
		}
		a.addNewState(namePrefix + "_" + exp);

		for (int i = 0; i < exp.length(); i++) {
			if (i == 0) {
				a.setInitial(namePrefix + "_epsilon");
				a.addTransition(namePrefix + "_epsilon", exp.charAt(i), namePrefix + "_" + exp.substring(i, i + 1));
			} else {
				if (exp.charAt(i) != '*') {
					if (i < exp.length() - 1 && exp.charAt(i + 1) == '*') {
						a.addTransition(namePrefix + "_" + exp.substring(0, i), exp.charAt(i),
								namePrefix + "_" + exp.substring(0, i));
					} else if (exp.charAt(i - 1) != '*') {
						a.addTransition(namePrefix + "_" + exp.substring(0, i), exp.charAt(i),
								namePrefix + "_" + exp.substring(0, i + 1));
					} else {
						a.addTransition(namePrefix + "_" + exp.substring(0, i - 2), exp.charAt(i),
								namePrefix + "_" + exp.substring(0, i + 1));
					}
				}
			}
		}
		a.setAccepting(namePrefix + "_" + exp);
	}

	/**
	 * Transpose automaton Note : mirror is cleared before the operation.
	 *
	 * @param original
	 *            : automaton to be transposed
	 * @param mirror
	 *            : receive the transposed automaton
	 */

	public static void transpose(Automaton original, AutomatonBuilder mirror) {
		/*
		 * All accepting become initial All initial become accepting
		 */
		mirror.clear();
		final String transposePrefix = "transpose_";
		Set<State> originalStates = original.getStates();
		Set<Character> alphabet = original.usedAlphabet();

		addTransposeStates(original, mirror, transposePrefix, originalStates);
		addReversedTransitions(original, mirror, transposePrefix, originalStates, alphabet);

	}

	private static void addReversedTransitions(Automaton original, AutomatonBuilder mirror, String transposePrefix,
			Set<State> originalStates, Set<Character> alphabet) {
		for (State previousState : originalStates) {
			for (Character usedChar : alphabet) {
				Set<State> transitionSet = original.getTransitionSet(previousState, usedChar);
				for (State nextState : transitionSet) {
					String prevNameInTr = getTransposeName(transposePrefix, previousState);
					String nextNameInTr = getTransposeName(transposePrefix, nextState);
					mirror.addTransition(nextNameInTr, usedChar, prevNameInTr);
				}
			}
		}
	}

	private static void addTransposeStates(Automaton original, AutomatonBuilder mirror, String transposePrefix,
			Set<State> originalStates) {
		for (State state : originalStates) {
			String newName = getTransposeName(transposePrefix, state);

			mirror.addNewState(newName);
			if (original.isAccepting(state) && original.isInitial(state)) {
				mirror.setInitial(newName);
				mirror.setAccepting(newName);
			} else if (original.isAccepting(state)) {
				mirror.setInitial(newName);
			} else if (original.isInitial(state)) {
				mirror.setAccepting(newName);
			}
		}
	}

	private static String getTransposeName(String transposePrefix, State state) {
		return transposePrefix + state.getName();
	}

	/**
	 * Determinization of nfa automaton. Note : dfa is cleared before the
	 * operation.
	 *
	 * @param nfa
	 *            : non deterministic automaton (to be determinize)
	 * @param dfa
	 *            : receive determinization result
	 */
	public static void determinize(Automaton nfa, AutomatonBuilder dfa) {
		// For each computed state set from nfa, a corresponding state has to be
		// created in dfa
		// map represents relationship between nfa state set (key) and created
		// dfa state (value)
		Map<Set<State>, State> map = new HashMap<Set<State>, State>();

		// stack todo contains state sets whose transitions have not yet been
		// computed
		Stack<Set<State>> todo = new Stack<Set<State>>();

		dfa.clear();

		Set<State> startSet = nfa.getInitialStates();

		// create matching state in DFA
		State start = dfa.addNewState(startSet.toString()); // state creation
		map.put(startSet, start); // record relationship in map

		dfa.setInitial(start); // start is the unique initial state of dfa

		todo.push(startSet); // put it in todo list.

		while (!todo.isEmpty()) {
			Set<State> fromSet = todo.pop(); // pick a state from todo list
			/*
			 * TODO : for each letter of alphabet : compute transitionSet from
			 * fromSet if computed set is a new one : create corresponding state
			 * in dfa record relationship in map add it to the todo list end if
			 * create corresponding transition in dfa
			 */

			Set<Character> alphabet = nfa.usedAlphabet();
			for (Character character : alphabet) {
				Set<State> transitionSet = nfa.getTransitionSet(fromSet, character);
				if (map.get(transitionSet) == null) {
					State state = dfa.addNewState(transitionSet.toString());
					map.put(transitionSet, state);
					todo.push(transitionSet);
				}
				dfa.addTransition(fromSet.toString(), character, transitionSet.toString());
			}

		}
		for (Set<State> qSet : map.keySet()) { // foreach computed state set
			/*
			 * TODO : if qset contains accepting state (from nfa) in dfa, set
			 * corresponding state as accepting state
			 */
			boolean containsAccepting = false;
			for (State state : qSet) {
				if (nfa.isAccepting(state)) {
					containsAccepting = true;
				}
			}
			if (containsAccepting) {
				dfa.setAccepting(qSet.toString());
			}

		}
	}

	/************** TP6 *********************/

	/**
	 * method non completed
	 * 
	 * @param words
	 * @param dest
	 */

	public static void createAhoCorasick(String[] words, AutomatonBuilder dest) {
		State racine = dest.addNewState("root");
		int lgMax = 0;
		State[] finBranche = new State[words.length];
		State q;

		for (int i = 0; i < words.length; i++) {
			finBranche[i] = racine;
			if (lgMax < words[i].length())
				lgMax = words[i].length();
		}

		for (int l = 0; l < lgMax; l++) {
			for (int i = 0; i < words.length; i++) {
				if (l < words[i].length()) {
					if (dest.getTransitionSet(finBranche[i], words[i].charAt(l)).size() == 0) {
						q = dest.addNewState("" + words[i].charAt(l));
						dest.addTransition(finBranche[i], words[i].charAt(l), q);
					} else {
						Set<State> states;
						states = dest.getTransitionSet(finBranche[i], words[i].charAt(l));
						q = states.iterator().next();
					}
					finBranche[i] = q;
					if (l + 1 == words[i].length()) {
						dest.setAccepting(finBranche[i]);
					}
				}
			}
		}
	}

	private static void createNewState(AutomatonBuilder dest, State father, char c) {
		State q = dest.addNewState("state");
		dest.addTransition(father, c, q);

		if (father.equals(dest.getInitialStates().iterator().next())) {

		}
	}

	private static void creerRepli(AutomatonBuilder dest, State father, State q) {
		State s = father;
		char c = findLetter(dest, father, q);
		State e = null;
		do {

		} while (e != null || s.equals(dest.getInitialStates().iterator().next()));
	}

	private static char findLetter(AutomatonBuilder dest, State in, State to) {
		Set<Character> characters = dest.usedAlphabet();
		for (char c : characters) {
			Set<State> states = dest.getTransitionSet(in, c);
			if (to.equals(states.iterator().next())) {
				return c;
			}
		}
		return 0;
	}

	/************** TP7 *********************/

	/**
	 * minimalise an automaton into a AutomatonBuilder
	 * 
	 * @param a
	 *            the automaton to minimalise
	 * @param dest
	 *            the minimalise automaton from a
	 */
	public static void minimalise(Automaton a, AutomatonBuilder dest) {

		AutomatonBuilder result = new NDAutomaton();

		transpose(a, result);
		determinize(result, dest);


		transpose(dest, result);
		TestND.dotToFile(result, "automate-test-minimal.dot");
		determinize(result, dest);
	}

}