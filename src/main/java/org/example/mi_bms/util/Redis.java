import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//解决中文乱码问题
@Configuration
public class Redis {

    @Bean
    public RedisTemplate redisTemplateInit(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        /**
         *
         * 设置Hash类型存储时，对象序列化报错解决
         */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
