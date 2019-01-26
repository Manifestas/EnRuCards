package dev.manifest.en_rucards.network;

import dev.manifest.en_rucards.BuildConfig;
import dev.manifest.en_rucards.data.model.Minicard;
import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LingvoApi {

    String EN_LANG = "1033";
    String RU_LANG = "1049";

    @Headers("Authorization: Basic " + BuildConfig.API_KEY)
    @POST("api/v1.1/authenticate")
    Call<String> getAuthToken();

    @GET("api/v1/Minicard?srcLang=" + EN_LANG + "&dstLang=" + RU_LANG)
    Maybe<Minicard> getTranslation(@Query("text") String ruText);

    @GET("api/v1/Sound?")
    Single<Response<ResponseBody>> getSoundFile(@Query("dictionaryName") String dictName,
                                                @Query("fileName") String fileName);
}
