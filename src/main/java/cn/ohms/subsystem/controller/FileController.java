// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/19.
package cn.ohms.subsystem.controller;

import cn.ohms.subsystem.common.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

/**
 * file controller
 * 资源管理，Resource Management
 *
 * <div>
 *     <p>文件上传和下载被分为“保密”和普通两种类型。</p>
 *     <p>1、其中保密的文件如果是非public文件则默认只有其拥有者能进行下载。如果是为pub文件，则所有用户都能够访问和下载。
 *     当然对于课群里面的资源，只要是老师上传的，那么默认情况下，课群里面的所有成员都是可以下载的。</p>
 *     <p>2、普通文件是指文章中的图片、视频等资源，这类资源是面向所有用户的。因此对这类资源没有进行任何加密、判权处理。普通资源的
 *     存储会按照一定的目录要求保存在磁盘中，生成的url地址同样具有特定的格式，后端按照指定的格式解析url地址得到物理path，如果资源
 *     存在直接读取资源并写回流中。</p>
 *     <p><b>PS:从性能考虑，普通资源可以选择使用nginx或者apache做静态代理，而后端也可用两者之一做反向代理。但是由于本项目处于完成
 *     作业的阶段，因此对于一些复杂的配置和面向正式生产环境的实现，初期直接忽略。希望后期这些功能都可以被实现。</b></p>
 * </div>
 *
 * <p>所有上传均使用POST方法提交，统一返回JSON数据。下载文件统一接受GET方法，存在直接返回文件数据，否则报404。</p>
 * <p>注意：没有实现续点断传，所有资源必须在响应后断开前传输完毕。</p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/user/file")
@Validated
public class FileController {
    /**
     * 上传文件资源
     *
     * @param genre    上传类型，common : 普通,sentinel:受保护
     * @param file     MultipartFile
     * @param isPublic 如果类型是sentinel，则可以指定该文件是否是公开的
     * @return ResponseResult
     */
    @PostMapping("/upload/{genre}")
    @ResponseBody
    @RequiresAuthentication
    public ResponseResult upload(@PathVariable @Pattern(regexp = "^(common)|(sentinel)$") String genre
            , @NotNull @RequestParam("file") MultipartFile file
            , @RequestParam(defaultValue = "false", required = false) Boolean isPublic) {
//        boolean isCommon = "common".equals(genre);
//        if (file.isEmpty() || Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
//            return ResponseResult.enError().setMsg("Do not accept empty file uploads");
//        }
//        String fileName = file.getOriginalFilename();
//        String fix = FileUtil.getFilePostfix(fileName);
//        String name = FileUtil.getFileNameNoFix(fileName);
//        if (fix.isEmpty() || !(isCommon ? resourceService.isCommonFileFormat(fix) : resourceService
//                .isConfidentialFileFormat(fix))) {
//            return ResponseResult.enFail().setMsg("Unsupported file type");
//        }
//        if (name.isEmpty()) {
//            return ResponseResult.enFail().setMsg("Unknown file");
//        }
//        String UUID = UUIDUtil.uuid32pure();
//        String filePath = NStringUtil.joint("{}/{}{}", new SimpleDateFormat("yyyy/MM/dd").format(new Date())
//                , UUID, fix);
//        File toFile = new File(NStringUtil.joint("{}/{}", isCommon ? ResourceService.commonFilePath
//                : ResourceService.confidentialFilePath, filePath));
//        try {
//            file.transferTo(toFile);
//            return (isCommon ? ResponseResult.enSuccess().add("file", filePath)
//                    : resourceService.saveConfidentialResource((UserEntity) SecurityUtils.getSubject().getSession()
//                    .getAttribute(UserService.USER_SELF), UUID, name, fix, toFile.getPath(), isPublic));
//        } catch (IOException e) {
//            log.warn("文件上传保存失败", e);
//        }
        return ResponseResult.enError();
    }
}
