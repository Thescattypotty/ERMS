package org.employee.rms.Security.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service // in real world i should use a db (like redis) to store the blacklisted tokens but for now its fine
public class BlackListService {
    private final Set<String> blackListedTokens = ConcurrentHashMap.newKeySet();

    public void addTokenToBlackList(String token) {
        blackListedTokens.add(token);
    }

    public boolean isTokenBlackListed(String token) {
        return blackListedTokens.contains(token);
    }
}
