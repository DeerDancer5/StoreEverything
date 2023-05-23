package com.projekt.projekt.Validation;

public @interface NotBlank {
    String message();
}
@interface Email
{
    String message();
}

@interface Password
{
    String message();
}
@interface Size {
    int min();

    int max();

    String msg();

}

