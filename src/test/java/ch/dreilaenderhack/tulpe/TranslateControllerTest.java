package ch.dreilaenderhack.tulpe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TranslateControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void translate() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"inputLanguage\": \"en\", \"outputLanguage\": \"de\", \"text\": \"The arrival announcement should be communicated in a couple of minutes!\"}");

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"text\":\"Die Ankunfts-Ankündigung sollte in ein paar Minuten erfolgen!\",\"originalTranslate\":\"Die Ankündigung der Ankunft sollte in ein paar Minuten erfolgen!\"}")));
    }

    @Test
    public void translateSameLanguageToSame() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"inputLanguage\": \"en\", \"outputLanguage\": \"en\", \"text\": \"Hello World!\"}");

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"googleJsonError\":{\"code\":400,\"errors\":[{\"domain\":\"global\",\"message\":\"Bad language pair: en|en\",\"reason\":\"badRequest\"}],\"message\":\"Bad language pair: en|en\"}}")));
    }
}