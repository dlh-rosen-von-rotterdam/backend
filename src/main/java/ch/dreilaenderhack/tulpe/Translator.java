package ch.dreilaenderhack.tulpe;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

class Translator {

    String translate(String inputLanguage, String outputLanguage, String text) {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        text,
                        Translate.TranslateOption.sourceLanguage(inputLanguage),
                        Translate.TranslateOption.targetLanguage(outputLanguage));

        return translation.getTranslatedText();
    }
}