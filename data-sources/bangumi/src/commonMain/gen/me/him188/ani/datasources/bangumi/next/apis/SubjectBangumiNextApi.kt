/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport",
)

package me.him188.ani.datasources.bangumi.next.apis

import me.him188.ani.datasources.bangumi.next.models.BangumiNextEditSubjectPostRequest
import me.him188.ani.datasources.bangumi.next.models.BangumiNextErrorResponse
import me.him188.ani.datasources.bangumi.next.models.BangumiNextGetSubjectEpisodeComments200ResponseInner
import me.him188.ani.datasources.bangumi.next.models.BangumiNextGetSubjectTopicsBySubjectId200Response
import me.him188.ani.datasources.bangumi.next.models.BangumiNextGroupReply
import me.him188.ani.datasources.bangumi.next.models.BangumiNextSubjectInterestComment
import me.him188.ani.datasources.bangumi.next.models.BangumiNextTopicCreation
import me.him188.ani.datasources.bangumi.next.models.BangumiNextTopicDetail

import me.him188.ani.datasources.bangumi.next.infrastructure.*
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.request.forms.formData
import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.json.Json
import io.ktor.http.ParametersBuilder
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

open class SubjectBangumiNextApi : ApiClient {

    constructor(
        baseUrl: String = ApiClient.BASE_URL,
        httpClientEngine: HttpClientEngine? = null,
        httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
        jsonSerializer: Json = ApiClient.JSON_DEFAULT
    ) : super(
        baseUrl = baseUrl,
        httpClientEngine = httpClientEngine,
        httpClientConfig = httpClientConfig,
        jsonBlock = jsonSerializer,
    )

    constructor(
        baseUrl: String,
        httpClient: HttpClient
    ) : super(baseUrl = baseUrl, httpClient = httpClient)

    /**
     * 删除自己创建的条目讨论版回复
     *
     * @param postID 
     * @return kotlin.String
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun deleteSubjectPost(postID: kotlin.Int): HttpResponse<kotlin.String> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.DELETE,
            "/p1/subjects/-/posts/{postID}".replace("{" + "postID" + "}", "$postID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }


    /**
     * 编辑自己创建的条目讨论版回复
     *
     * @param postID
     * @param bangumiNextEditSubjectPostRequest 
     * @return kotlin.String
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun editSubjectPost(
        postID: kotlin.Int,
        bangumiNextEditSubjectPostRequest: BangumiNextEditSubjectPostRequest
    ): HttpResponse<kotlin.String> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = bangumiNextEditSubjectPostRequest

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.PUT,
            "/p1/subjects/-/posts/{postID}".replace("{" + "postID" + "}", "$postID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return jsonRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }



    /**
     * 编辑自己创建的条目讨论版
     *
     * @param topicID 
     * @param bangumiNextTopicCreation  (optional)
     * @return kotlin.String
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun editSubjectTopic(
        topicID: kotlin.Int,
        bangumiNextTopicCreation: BangumiNextTopicCreation? = null
    ): HttpResponse<kotlin.String> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = bangumiNextTopicCreation

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.PUT,
            "/p1/subjects/-/topics/{topicID}".replace("{" + "topicID" + "}", "$topicID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return jsonRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }



    /**
     * 获取条目的剧集评论
     *
     * @param episodeID 
     * @return kotlin.collections.List<BangumiNextGetSubjectEpisodeComments200ResponseInner>
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun getSubjectEpisodeComments(episodeID: kotlin.Int): HttpResponse<kotlin.collections.List<BangumiNextGetSubjectEpisodeComments200ResponseInner>> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/p1/subjects/-/episode/{episodeID}/comments".replace("{" + "episodeID" + "}", "$episodeID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap<GetSubjectEpisodeCommentsResponse>().map { value }
    }

    @Serializable(GetSubjectEpisodeCommentsResponse.Companion::class)
    private class GetSubjectEpisodeCommentsResponse(val value: List<BangumiNextGetSubjectEpisodeComments200ResponseInner>) {
        companion object : KSerializer<GetSubjectEpisodeCommentsResponse> {
            private val serializer: KSerializer<List<BangumiNextGetSubjectEpisodeComments200ResponseInner>> =
                serializer<List<BangumiNextGetSubjectEpisodeComments200ResponseInner>>()
            override val descriptor = serializer.descriptor
            override fun serialize(encoder: Encoder, obj: GetSubjectEpisodeCommentsResponse) =
                serializer.serialize(encoder, obj.value)

            override fun deserialize(decoder: Decoder) =
                GetSubjectEpisodeCommentsResponse(serializer.deserialize(decoder))
        }
    }

    /**
     * 获取条目讨论版回复
     *
     * @param postID 
     * @return BangumiNextGroupReply
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun getSubjectPost(postID: kotlin.Int): HttpResponse<BangumiNextGroupReply> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/p1/subjects/-/posts/{postID}".replace("{" + "postID" + "}", "$postID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }


    /**
     * 获取帖子列表
     *
     * @param id 
     * @return BangumiNextTopicDetail
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun getSubjectTopicDetail(id: kotlin.Int): HttpResponse<BangumiNextTopicDetail> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/p1/subjects/-/topics/{id}".replace("{" + "id" + "}", "$id"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }


    /**
     * 获取条目讨论版列表
     *
     * @param subjectID 
     * @param limit  (optional, default to 30)
     * @param offset  (optional, default to 0)
     * @return BangumiNextGetSubjectTopicsBySubjectId200Response
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun getSubjectTopicsBySubjectId(
        subjectID: kotlin.Int,
        limit: kotlin.Int? = 30,
        offset: kotlin.Int? = 0
    ): HttpResponse<BangumiNextGetSubjectTopicsBySubjectId200Response> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        limit?.apply { localVariableQuery["limit"] = listOf("$limit") }
        offset?.apply { localVariableQuery["offset"] = listOf("$offset") }
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/p1/subjects/{subjectID}/topics".replace("{" + "subjectID" + "}", "$subjectID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }


    /**
     * 获取条目的吐槽箱
     *
     * @param subjectID 
     * @param limit  (optional, default to 20)
     * @param offset  (optional, default to 0)
     * @return BangumiNextSubjectInterestComment
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun subjectComments(
        subjectID: kotlin.Int,
        limit: kotlin.Int? = 20,
        offset: kotlin.Int? = 0
    ): HttpResponse<BangumiNextSubjectInterestComment> {

        val localVariableAuthNames = listOf<String>()

        val localVariableBody = 
            io.ktor.client.utils.EmptyContent

        val localVariableQuery = mutableMapOf<String, List<String>>()
        limit?.apply { localVariableQuery["limit"] = listOf("$limit") }
        offset?.apply { localVariableQuery["offset"] = listOf("$offset") }
        val localVariableHeaders = mutableMapOf<String, String>()

        val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/p1/subjects/{subjectID}/comments".replace("{" + "subjectID" + "}", "$subjectID"),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
        )

        return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames,
        ).wrap()
    }


}
