package com.weg.infoweg.core.abstractions;

import java.util.UUID;

public interface UseCase<INPUT, OUTPUT> {

    public OUTPUT execute(INPUT input, UUID id);
}
