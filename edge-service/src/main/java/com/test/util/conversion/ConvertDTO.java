package com.test.util.conversion;

import com.test.thrift.user.UserInfo;
import com.test.thrift.user.dto.UserDTO;
import org.springframework.beans.BeanUtils;

public class ConvertDTO {
    // 为了减少空间的浪费，将 UserInfo 转换为可序列化的 UserDTO
    // UserDTO 建议放在 user-thrift-service-api 模块中 com.test.thrift.user.dto.UserDTO
    // 因为UserDTO和UserInfo的属性是一致的，直接调用BeanUtils中的属性复制即可
    public static UserDTO toUserDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        // 复制属性，从userInfo到userDTO
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }
}
