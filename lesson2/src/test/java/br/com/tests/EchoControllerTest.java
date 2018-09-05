package br.com.tests;

import br.com.tests.controller.EchoController;
import br.com.tests.controller.service.IMessageService;
import br.com.tests.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(controllers = EchoController.class, secure = false)
public class EchoControllerTest {

    private static final String ECHO_URI = "/echo";
    private static final String MESSAGE_URI = "/message";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private IMessageService messageService;

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mvc = builder.build();
    }

    @Test
    public final void whenCallEchoWithMessageThenReturnSameMessage() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(ECHO_URI + "/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("hello"));
    }

    @Test
    public final void whenPostCreateWithMessageThenReturnSameMessage() throws Exception {

        Message message = new Message("CREATED MESSAGE");

        String jsonMessage = objectMapper.writeValueAsString(message);

        when(messageService.create(any(Message.class))).thenReturn(message);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(MESSAGE_URI)
                .content(jsonMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(message.getMessage()))
                .andDo(print());


    }
}
