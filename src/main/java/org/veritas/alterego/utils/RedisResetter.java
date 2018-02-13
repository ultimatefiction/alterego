package org.veritas.alterego.utils;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

public class RedisResetter {

    public static void main(String[] args) {

        RedissonClient client = new RedissonClientProvider().getClient();
        RSet<String> chatSet = client.getSet("alterego:groups");
        chatSet.readAll().forEach(s -> client.getSet(s).delete());

    }

}
