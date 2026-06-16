package pl.dmcs.jmazur.service;

public interface ReCaptchaService {

    boolean verify(String captcha);
}
