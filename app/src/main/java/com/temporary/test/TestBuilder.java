package com.temporary.test;

public class TestBuilder {
    private int id;

    public static class Builder {
        private int id;


        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public TestBuilder build() {
            return new TestBuilder(this);
        }
    }

    public TestBuilder(Builder builder) {
        this.id = builder.id;
    }
}
