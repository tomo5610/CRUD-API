package com.tomoyasu.crudapi.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NameRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 全ての名前を取得した際にステータスコードが200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/names"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    [
                        {
                            "name": "tomoyasu",
                            "birth": "2023-01"
                        },
                        {
                            "name": "tanaka",
                            "birth": "2023-02"
                        },
                        {
                            "name": "yamada",
                            "birth": "2023-03"
                        }
                    ]
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 存在する指定したIDの名前を取得した際にステータスコードが200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/names/1")).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                        "name": "tomoyasu",
                        "birth": "2023-01"
                    }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 存在しないIDを指定して名前を取得するとレスポンスが404を返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/names/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/insert_names.yml", ignoreCols = "id")
    @Transactional
    void 名前を新規登録できること() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/names")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "higashi",
                                    "birth": "2023-01"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");
        assertThat(locationHeader).isNotBlank();

        int actualId = extractIdFromLocationHeader(locationHeader);

        String expectedLocation = "/names/" + actualId;

        assertThat(locationHeader).endsWith(expectedLocation);

        String responseBody = result.getResponse().getContentAsString();

        JSONAssert.assertEquals("""
                {
                    "name": "higashi",
                    "birth": "2023-01"
                }
                """, responseBody, JSONCompareMode.LENIENT);
    }

    private int extractIdFromLocationHeader(String locationHeader) {
        try {
            URL url = new URL(locationHeader);

            String path = url.getPath();
            String[] segments = path.split("/");
            String lastSegment = segments[segments.length - 1];

            return Integer.parseInt(lastSegment);
        } catch (MalformedURLException | NumberFormatException e) {
            throw new RuntimeException("Failed to extract id from Location header: " + locationHeader, e);
        }
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/update_names.yml")
    @Transactional
    void 指定したIDの名前を更新できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/names/3")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                    {
                                        "name": "suzuki",
                                        "birth": "2023-10"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                        "message": "successfully updated"
                    }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 存在しないIDの名前を更新した際にエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/names/99")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                    {
                                        "name": "huruya",
                                        "birth": "2023-01"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                       "error": "Not Found",
                       "message": "resource not found",
                       "status": "404"
                    }
                """, response, new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("timestamp", (o1, o2) -> true)));

    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/delete_names.yml")
    @Transactional
    void 名前を1件削除できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/names/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                        "message": "successfully delete"
                    }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 存在しないIDを削除した際のレスポンスが404を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/names/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    {
                       "error": "Not Found",
                       "message": "resource not found",
                       "status": "404"
                    }
                """, response, new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("timestamp", (o1, o2) -> true)));
    }
}
