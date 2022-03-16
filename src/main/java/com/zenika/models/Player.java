package com.zenika.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

import static com.zenika.utils.StringUtil.nullAsStringIfEmpty;

@JsonDeserialize(builder = Player.Builder.class)
@SuppressWarnings("unused")
public interface Player {
    @JsonGetter("id") Long id();
    @JsonGetter("name") String name();

    static Player of(Long id, String name) {
        return new Player() {
            @Override
            public Long id() {
                return id;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public int hashCode() {
                return Objects.hash(id(), name());
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this)
                    return true;
                if(!(obj instanceof Player))
                    return false;
                var that = (Player) obj;
                return Objects.equals(id(), that.id())
                    && Objects.equals(name(), that.name());
            }

            @Override
            public String toString() {
                return "{\n" +
                    "\"id\": " + id() + ",\n" +
                    "\"name\": " + nullAsStringIfEmpty(name()) +
                    "\n}";
            }
        };
    }

    @JsonPOJOBuilder(withPrefix = "")
    class Builder {
        private Long id;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Player build() {
            return Player.of(id, name);
        }
    }
}
