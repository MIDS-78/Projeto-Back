package com.weg.infoweg.core.abstractions;

public interface UseCase<INPUT, OUTPUT> {

    public OUTPUT execute(INPUT input);
}
