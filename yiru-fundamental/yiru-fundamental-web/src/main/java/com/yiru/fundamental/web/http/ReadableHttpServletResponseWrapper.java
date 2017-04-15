package com.yiru.fundamental.web.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Created by Baowen on 2017/4/15.
 */
public class ReadableHttpServletResponseWrapper extends HttpServletResponseWrapper {

    PrintWriterWithCopy printWriterWithCopy = null;
    OutputStreamWithCopy outputStreamWithCopy = null;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public ReadableHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
    }


    public String getResponseBody() throws IOException {
        if (printWriterWithCopy != null) {
            return printWriterWithCopy.getCopyString();
        }
        if (outputStreamWithCopy != null) {
            return new String(outputStreamWithCopy.getCopyByteArray(), getCharacterEncoding());
        }
        throw new IllegalStateException("Neither getWriter() or getOutputStream() had been called!");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriterWithCopy == null) {
            printWriterWithCopy = new PrintWriterWithCopy(super.getWriter());
        }
        return printWriterWithCopy;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputStreamWithCopy == null) {
            outputStreamWithCopy = new OutputStreamWithCopy(super.getOutputStream());
        }
        return outputStreamWithCopy;
    }

    class PrintWriterWithCopy extends PrintWriter {

        private StringBuilder copyString = new StringBuilder();

        public PrintWriterWithCopy(Writer writer) {
            super(writer);
        }

        @Override
        public void write(int c) {
            copyString.append((char) c); // It is actually a char, not an int.
            super.write(c);
        }

        @Override
        public void write(char[] chars, int offset, int length) {
            copyString.append(chars, offset, length);
            super.write(chars, offset, length);
        }

        @Override
        public void write(String string, int offset, int length) {
            copyString.append(string, offset, length);
            super.write(string, offset, length);
        }

        public String getCopyString() {
            return copyString.toString();
        }

    }

    class OutputStreamWithCopy extends ServletOutputStream {
        private OutputStream outputStream;
        private ByteArrayOutputStream copy;

        public OutputStreamWithCopy(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.copy = new ByteArrayOutputStream(1024);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            copy.write(b);
        }

        public byte[] getCopyByteArray() {
            return copy.toByteArray();
        }
    }
}
