package com.alaan.outn.interfac;


public interface CommentListener<T> {
    void onClick(boolean isReply, T item);
}
