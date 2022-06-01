package com.example.vetmobile.DateBase.Model;

import java.util.List;

public class AnimailModel {

        private int id;
        private String Nickname_Animal;
        private String Type_Animal;
        private String Age_Animal;
        private int User_id;
        private List<RenderModel> Render;

        public List<RenderModel> getRender() {
            return Render;
        }

        public int getId() {
            return id;
        }

        public String getNickname_Animal() {
            return Nickname_Animal;
        }

        public String getType_Animal() {
            return Type_Animal;
        }

        public String getAge_Animal() {
            return Age_Animal;
        }

        public int getUser_id() {
            return User_id;
        }

        public class ArrayAnimal {
            public ArrayAnimal(AnimailModel Animail) {
            }

            public ArrayAnimal() {
            }
        }

    }

