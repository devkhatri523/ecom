package org.asymptotes.ecommerce.handler;

import java.util.Map;

public record ErrorReponse(Map<String,String> errors) {
}
