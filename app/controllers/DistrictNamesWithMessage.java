package controllers;

public class DistrictNamesWithMessage{
        private String[] districts;
        private String message;
        public DistrictNamesWithMessage(){
        }

        public DistrictNamesWithMessage(String[] districts, String message) {
            this.districts = districts;
            this.message = message;
        }

        public String[] getDistricts() {
            return districts;
        }

        public void setDistricts(String[] districts) {
            this.districts = districts;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
