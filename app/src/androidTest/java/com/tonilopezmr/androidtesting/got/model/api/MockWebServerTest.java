package com.tonilopezmr.androidtesting.got.model.api;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class MockWebServerTest {

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
    }

    @After
    public void tearDown() throws Exception {
        this.server.shutdown();
    }

    protected String getBaseEndpoint() {
        return server.url("/").toString();
    }

    protected void assertGetRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
        assertEquals("GET", request.getMethod());
    }

    protected void assertPostRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
        assertEquals("POST", request.getMethod());
    }

    protected void assertRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
    }

    protected void assertRequestBodyFromFileEquals(String jsonFile) throws InterruptedException, IOException {
        RecordedRequest request = server.takeRequest();
        assertEquals(getContentFromFile(jsonFile), request.getBody().readUtf8());
    }

    protected void assertRequestBodyEquals(String jsonRequest) throws InterruptedException, IOException {
        RecordedRequest request = server.takeRequest();
        assertEquals(jsonRequest, request.getBody().readUtf8());
    }

    protected void enqueueMockResponse() throws IOException {
        enqueueMockResponse(200);
    }

    protected void enqueueMockResponse(int code) throws IOException {
        enqueueMockResponse(code, null);
    }

    protected void enqueueMockResponse(int code, String fileName) throws IOException {
        MockResponse mockResponse = new MockResponse();
        String fileContent = getContentFromFile(fileName);
        mockResponse.setResponseCode(code);
        mockResponse.setBody(fileContent);
        server.enqueue(mockResponse);
    }

    protected void enqueueMockResponse(String body) throws IOException {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200);
        mockResponse.setBody(body);
        server.enqueue(mockResponse);
    }

    protected void assertRequestContainsHeader(String key, String expectedValue)
            throws InterruptedException {
        assertRequestContainsHeader(key, expectedValue, 0);
    }

    protected void assertRequestContainsHeader(String key, String expectedValue, int requestIndex)
            throws InterruptedException {
        RecordedRequest recordedRequest = getRecordedRequestAtIndex(requestIndex);
        String value = recordedRequest.getHeader(key);
        assertEquals(expectedValue, value);
    }

    private RecordedRequest getRecordedRequestAtIndex(int requestIndex) throws InterruptedException {
        RecordedRequest request = null;
        for (int i = 0; i <= requestIndex; i++) {
            request = server.takeRequest();
        }
        return request;
    }

    protected String getContentFromFile(String fileName) throws IOException {
        if (fileName == null) {
            return "";
        }

        fileName = getClass().getResource("/"+ fileName).getFile();
        File file = new File(fileName);
        return convertFileToString(file);
    }


//    public String getContentFromFile(String filePath) throws IOException {
//        if (filePath == null){
//            return "";
//        }
//
//        ClassLoader classLoader = getClass().getClassLoader();
//        URL resource = classLoader.getResource(filePath);
//        File file = new File(resource.getPath());
//        return convertFileToString(file);
//    }

    public String convertFileToString(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
