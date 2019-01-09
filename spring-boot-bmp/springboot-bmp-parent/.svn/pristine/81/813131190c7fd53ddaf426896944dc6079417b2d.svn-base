package top.ywwxhz.utils;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.*;

/**
 * Gson工具类
 * 
 * @author ywwxhz
 *
 */
public class GSONTools {
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private static final Gson gson2 = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private GSONTools() {
    }

    /**
     * This method serializes the specified object into its equivalent Json
     * representation. This method should be used when the specified object is
     * not a generic type. This method uses {@link Class#getClass()} to get the
     * type for the specified object, but the {@code getClass()} loses the
     * generic type information because of the Type Erasure feature of Java.
     * Note that this method works fine if the any of the object fields are of
     * generic type, just the object itself should not be of a generic type. If
     * the object is of generic type, use {@link #toJson(Object, Type)} instead.
     * If you want to write out the object to a {@link Writer}, use
     * {@link #toJson(Object, Appendable)} instead.
     *
     * @param src
     *            the object for which Json representation is to be created
     *            setting for Gson
     * @return Json representation of {@code src}.
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }
    
    /**
     * This method serializes the specified object into its equivalent Json
     * representation. This method should be used when the specified object is
     * not a generic type. This method uses {@link Class#getClass()} to get the
     * type for the specified object, but the {@code getClass()} loses the
     * generic type information because of the Type Erasure feature of Java.
     * Note that this method works fine if the any of the object fields are of
     * generic type, just the object itself should not be of a generic type. If
     * the object is of generic type, use {@link #toJson(Object, Type)} instead.
     * If you want to write out the object to a {@link Writer}, use
     * {@link #toJson(Object, Appendable)} instead.
     *
     * @param src
     *            the object for which Json representation is to be created
     *            setting for Gson
     * @return Json representation of {@code src}.
     */
    public static String toPrettyJson(Object src) {
        return gson2.toJson(src);
    }

    /**
     * This method deserializes the specified Json into an object of the
     * specified class. It is not suitable to use if the specified class is a
     * generic type since it will not have the generic type information because
     * of the Type Erasure feature of Java. Therefore, this method should not be
     * used if the desired type is a generic type. Note that this method works
     * fine if the any of the fields of the specified object are generics, just
     * the object itself should not be a generic type. For the cases when the
     * object is of generic type, invoke {@link #fromJson(String, Type)}. If you
     * have the Json in a {@link Reader} instead of a String, use
     * {@link #fromJson(Reader, Class)} instead.
     *
     * @param <T>
     *            the type of the desired object
     * @param json
     *            the string from which the object is to be deserialized
     * @param classOfT
     *            the class of T
     * @return an object of type T from the string. Returns {@code null} if
     *         {@code json} is {@code null}.
     * @throws JsonSyntaxException
     *             if json is not a valid representation for an object of type
     *             classOfT
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    /**
     * This method deserializes the specified Json into an object of the
     * specified type. This method is useful if the specified object is a
     * generic type. For non-generic objects, use
     * {@link #fromJson(String, Class)} instead. If you have the Json in a
     * {@link Reader} instead of a String, use {@link #fromJson(Reader, Type)}
     * instead.
     *
     * @param <T>
     *            the type of the desired object
     * @param json
     *            the string from which the object is to be deserialized
     * @param typeOfT
     *            The specific genericized type of src. You can obtain this type
     *            by using the {@link com.google.gson.reflect.TypeToken} class.
     *            For example, to get the type for {@code Collection<Foo>}, you
     *            should use:
     * 
     *            <pre>
     *            Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;() {
     *            }.getType();
     *            </pre>
     * 
     * @return an object of type T from the string. Returns {@code null} if
     *         {@code json} is {@code null}.
     * @throws JsonParseException
     *             if json is not a valid representation for an object of type
     *             typeOfT
     * @throws JsonSyntaxException
     *             if json is not a valid representation for an object of type
     */
    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * This method deserializes the Json read from the specified reader into an
     * object of the specified type. This method is useful if the specified
     * object is a generic type. For non-generic objects, use
     * {@link #fromJson(Reader, Class)} instead. If you have the Json in a
     * String form instead of a {@link Reader}, use
     * {@link #fromJson(String, Type)} instead.
     *
     * @param <T>
     *            the type of the desired object
     * @param json
     *            the reader producing Json from which the object is to be
     *            deserialized
     * @param typeOfT
     *            The specific genericized type of src. You can obtain this type
     *            by using the {@link com.google.gson.reflect.TypeToken} class.
     *            For example, to get the type for {@code Collection<Foo>}, you
     *            should use:
     * 
     *            <pre>
     *            Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;() {
     *            }.getType();
     *            </pre>
     * 
     * @return an object of type T from the json. Returns {@code null} if
     *         {@code json} is at EOF.
     * @throws JsonIOException
     *             if there was a problem reading from the Reader
     * @throws JsonSyntaxException
     *             if json is not a valid representation for an object of type
     * @since 1.2
     */
    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * This method serializes the specified object into its equivalent
     * representation as a tree of {@link JsonElement}s. This method should be
     * used when the specified object is not a generic type. This method uses
     * {@link Class#getClass()} to get the type for the specified object, but
     * the {@code getClass()} loses the generic type information because of the
     * Type Erasure feature of Java. Note that this method works fine if the any
     * of the object fields are of generic type, just the object itself should
     * not be of a generic type. If the object is of generic type, use
     * {@link #toJsonTree(Object, Type)} instead.
     *
     * @param src
     *            the object for which Json representation is to be created
     *            setting for Gson
     * @return Json representation of {@code src}.
     * @since 1.4
     */
    public static JsonElement toJsonTree(Object src) {
        return gson.toJsonTree(src);
    }
}
