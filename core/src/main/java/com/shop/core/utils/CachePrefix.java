package com.shop.core.utils;

public enum CachePrefix {

      GOODS;

    public String getPrefix() {
        return "{" + this.name() + "}_";
    }

}
