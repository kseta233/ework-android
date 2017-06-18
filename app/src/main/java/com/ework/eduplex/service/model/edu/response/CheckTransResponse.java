package com.ework.eduplex.service.model.edu.response;

import com.ework.eduplex.service.model.Meta;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/5/17.
 */

public class CheckTransResponse {
        @SerializedName("meta")
        Meta meta;

        @SerializedName("data")
        CheckTransResponseData data;

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public CheckTransResponseData getData() {
            return data;
        }

        public void setData(CheckTransResponseData data) {
            this.data = data;
        }
}
