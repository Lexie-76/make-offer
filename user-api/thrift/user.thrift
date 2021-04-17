namespace java com.test.thrift.user

# 定义用户的实体(类), 使用 struct
struct UserInfo {
    1:i32 id,
    2:string username,
    3:string password,
    4:string email
}

# 声明用户服务的接口
service UserService {
    # 注册用户
    void registerUser(1:UserInfo userInfo);

    # 根据用户名获取用户信息
    UserInfo getUserByName(1:string username);

    # 根据 id 获取用户信息
    UserInfo getUserById(1:i32 id);
}