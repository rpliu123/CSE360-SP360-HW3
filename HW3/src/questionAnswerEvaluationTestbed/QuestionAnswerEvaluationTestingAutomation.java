package questionAnswerEvaluationTestbed;

public class QuestionAnswerEvaluationTestingAutomation {
	
	static int numPassed = 0;	// Counter of the number of passed tests
	static int numFailed = 0;	// Counter of the number of failed tests

	/*
	 * This mainline displays a header to the console, performs a sequence of
	 * test cases, and then displays a footer with a summary of the results
	 */
	public static void main(String[] args) {
		/************** Test cases semi-automation report header **************/
		System.out.println("______________________________________");
		System.out.println("\nQuestion-Answer Testing Automation");

		/************** Start of the test cases **************/
		
		// Test case 1: Valid question and answer about an assignment
		performTestCase(1, "How do we solve problem 4 on the assignment?", "We solve it using Java in Eclipse.", true);
		
		// Test case 2: Invalid question (too short)
		performTestCase(2, "Why?", "Because that's how it works.", true);
		
		// Test case 3: Invalid answer (contains profanity)
		performTestCase(3, "I am confused on how to do problem 5 on the worksheet, how do we solve it?", "It's pretty damn easy I can help you.", false);
		
		// Test case 4: Invalid question (contains profanity)
		performTestCase(4, "How the hell are we supposed to do this assignment?", "We do it by solving the problem one at a time.", false);
		
		// Test case 5: Valid question and answer with detailed explanation
		performTestCase(5, "Can you explain the assignment with more information? I am confused.", "So the assignment says that we need to start off with our Java project. We then need to create 5 different tests to test our functionality of our project.", true);
		
		// Test case 6: Invalid Question and Answer
		performTestCase(6, "Idk?", "Ok.", false);
		
		/************** End of the test cases **************/
		
		/************** Test cases semi-automation report footer **************/
		System.out.println("____________________________________________________________________________");
		System.out.println();
		System.out.println("Number of tests passed: "+ numPassed);
		System.out.println("Number of tests failed: "+ numFailed);
	}
	
	/*
	 * This method sets up the input values for the test from the input parameters,
	 * displays test execution information, invokes the recognizer,
	 * interprets the returned value, and displays the interpreted result.
	 */
	private static void performTestCase(int testCase, String question, String answer, boolean expectedPass) {
		/************** Display an individual test case header **************/
		System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
		System.out.println("Question: \"" + question + "\"");
		System.out.println("Answer: \"" + answer + "\"");
		System.out.println("______________");
		System.out.println("\nEvaluation execution trace:");
		
		/************** Call the evaluator to process the input **************/
		String resultText = QuestionAnswerEvaluator.evaluateQuestionAnswer(question, answer);
		
		/************** Interpret the result and display that interpreted information **************/
		System.out.println();
		
		// If the resulting text is not empty, the evaluator rejected the input
		if (!resultText.isEmpty()) {
			 // If the test case expected the test to pass then this is a failure
			if (expectedPass) {
				System.out.println("***Failure*** The question-answer pair is invalid." + 
						"\nBut it was supposed to be valid, so this is a failure!\n");
				System.out.println("Error message: " + resultText);
				numFailed++;
			}
			// If the test case expected the test to fail then this is a success
			else {			
				System.out.println("***Success*** The question-answer pair is invalid." + 
						"\nBut it was supposed to be invalid, so this is a pass!\n");
				System.out.println("Error message: " + resultText);
				numPassed++;
			}
		}
		
		// If the resulting text is empty, the evaluator accepted the input
		else {	
			// If the test case expected the test to pass then this is a success
			if (expectedPass) {	
				System.out.println("***Success*** The question-answer pair is valid, so this is a pass!");
				numPassed++;
			}
			// If the test case expected the test to fail then this is a failure
			else {
				System.out.println("***Failure*** The question-answer pair was judged as valid" + 
						"\nBut it was supposed to be invalid, so this is a failure!");
				numFailed++;
			}
		}
		displayEvaluation();
	}
	
	private static void displayEvaluation() {
		if (QuestionAnswerEvaluator.questionHasMinLength)
			System.out.println("Question meets minimum length requirement - Satisfied");
		else
			System.out.println("Question meets minimum length requirement - Not Satisfied");

		if (QuestionAnswerEvaluator.answerHasMinLength)
			System.out.println("Answer meets minimum length requirement - Satisfied");
		else
			System.out.println("Answer meets minimum length requirement - Not Satisfied");
	
		if (QuestionAnswerEvaluator.questionNoProfanity)
			System.out.println("Question contains no profanity - Satisfied");
		else
			System.out.println("Question contains no profanity - Not Satisfied");

		if (QuestionAnswerEvaluator.answerNoProfanity)
			System.out.println("Answer contains no profanity - Satisfied");
		else
			System.out.println("Answer contains no profanity - Not Satisfied");

		if (QuestionAnswerEvaluator.answerRelevantToQuestion)
			System.out.println("Answer is relevant to question - Satisfied");
		else
			System.out.println("Answer is relevant to question - Not Satisfied");
	}
}