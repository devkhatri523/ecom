package org.asymptotes.payment.handler;

import java.util.Map;

public record ErrorReponse(Map<String,String> errors) {
}
