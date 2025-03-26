package questionAnswerEvaluationTestbed;

import java.util.Arrays;
import java.util.List;

public class QuestionAnswerEvaluator {
    
    // Flags for various criteria checks
    public static boolean questionHasMinLength = false;
    public static boolean answerHasMinLength = false;
    public static boolean questionNoProfanity = false;
    public static boolean answerNoProfanity = false;
    public static boolean answerRelevantToQuestion = false;
    
    // Constants
    private static final int MIN_QUESTION_LENGTH = 5; // Minimum number of characters for a question
    private static final int MIN_ANSWER_LENGTH = 10;  // Minimum number of characters for an answer
    
    // List of profane words to check against
    private static final List<String> PROFANITY_LIST = Arrays.asList(
            "damn", "hell", "shit", "fuck", "crap", "ass"
    );
    
    /**
     * Evaluates a question-answer pair for validity
     * @param question The question provided by the user
     * @param answer The answer provided by the user
     * @return An empty string if valid, or an error message if invalid
     */
    public static String evaluateQuestionAnswer(String question, String answer) {
        // Reset all flags
        resetFlags();
        
        // Initialize all flags as true first
        questionNoProfanity = true;
        answerNoProfanity = true;
        
        // Check question length
        if (question.length() >= MIN_QUESTION_LENGTH) {
            questionHasMinLength = true;
        } else {
            return "Question is too short. It must be at least " + MIN_QUESTION_LENGTH + " characters.";
        }
        
        // Check answer length
        if (answer.length() >= MIN_ANSWER_LENGTH) {
            answerHasMinLength = true;
        } else {
            return "Answer is too short. It must be at least " + MIN_ANSWER_LENGTH + " characters.";
        }
        
        // Check question for profanity
        if (containsProfanity(question)) {
            questionNoProfanity = false;
            return "Question contains inappropriate language.";
        }
        
        // Check answer for profanity
        if (containsProfanity(answer)) {
            answerNoProfanity = false;
            return "Answer contains inappropriate language.";
        }
        
        // Check if answer is relevant to the question
        if (isAnswerRelevant(question, answer)) {
            answerRelevantToQuestion = true;
        } else {
            return "Answer does not appear to be relevant to the question.";
        }
        
        // If all checks pass, return empty string indicating validity
        return "";
    }
    
    /**
     * Checks if the text contains any words from the profanity list
     * @param text The text to check
     * @return true if profanity is found, false otherwise
     */
    private static boolean containsProfanity(String text) {
        String lowerText = text.toLowerCase();
        
        // Use word boundaries to avoid false positives
        // For example, "class" shouldn't match "ass"
        for (String word : PROFANITY_LIST) {
            // Check for word boundaries (space, punctuation, or beginning/end of string)
            String regex = "\\b" + word + "\\b";
            if (lowerText.matches(".*" + regex + ".*")) {
                System.out.println("Profanity detected: " + word);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determines if an answer is relevant to a question by checking for common keywords
     * @param question The question text
     * @param answer The answer text
     * @return true if the answer appears relevant, false otherwise
     */
    private static boolean isAnswerRelevant(String question, String answer) {
        // Extract key words from question (simple implementation)
        String[] questionWords = question.toLowerCase().replaceAll("[?.,!]", "").split("\\s+");
        String answerLower = answer.toLowerCase();
        
        // Check if at least one significant word from the question appears in the answer
        // Skip common words like "is", "the", "a", etc.
        List<String> commonWords = Arrays.asList("is", "the", "a", "an", "of", "in", "on", "at", 
                "to", "for", "with", "by", "about", "like", "what", "how", "why", "when", "who");
        
        for (String word : questionWords) {
            if (word.length() > 2 && !commonWords.contains(word) && answerLower.contains(word)) {
                return true;
            }
        }
        
        // If the answer contains the topic of the question, it's likely relevant
        // For a more sophisticated implementation, this could use natural language processing
        
        // Simple fallback: if the answer is long enough, consider it relevant
        // A more advanced implementation would use semantic analysis
        if (answer.length() > 50) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Reset all validation flags
     */
    private static void resetFlags() {
        questionHasMinLength = false;
        answerHasMinLength = false;
        questionNoProfanity = false;
        answerNoProfanity = false;
        answerRelevantToQuestion = false;
    }
}