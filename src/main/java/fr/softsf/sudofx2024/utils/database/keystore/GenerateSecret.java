package fr.softsf.sudofx2024.utils.database.keystore;

import fr.softsf.sudofx2024.annotations.ExcludedFromCoverageReportGenerated;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import static org.passay.IllegalCharacterRule.ERROR_CODE;


public final class GenerateSecret {

    @ExcludedFromCoverageReportGenerated
    private GenerateSecret() {
    }

    /**
     * Define the secret special characters
     *
     * @return The special characters
     */
    private static CharacterData createSpecialChars() {
        return new CharacterData() {
            @ExcludedFromCoverageReportGenerated
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&()";
            }
        };
    }

    /**
     * Generate Passay secret
     *
     * @return The Passay secret
     */
    public static String generatePassaySecret() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        CharacterData specialChars = createSpecialChars();
        CharacterRule specialCharsRule = new CharacterRule(specialChars);
        specialCharsRule.setNumberOfCharacters(2);
        return gen.generatePassword(24, specialCharsRule, lowerCaseRule, upperCaseRule, digitRule);
    }
}
