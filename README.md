# zywork-generator

zywork-generator为基于SpringBoot的自动代码生成器的后台部分，其依赖于zywork-commom（[https://github.com/gz-zywork/zywork-common](https://github.com/gz-zywork/zywork-common)）项目。

在使用此自动代码生成器之前，先使用Maven提供的mvn install命令安装zywork-common的jar包到Maven本地仓库中，也可以clone zywork-common项目代码，在IDE中mvn install。

zywork-generator可以自动生成DO, DTO, VO, Query等Bean对象，也可以自动生成Mapper, DAO, Service及Service实现类和Controller类，同时还可以生成基于iview及iview-admin的后台前端代码。

zywork-generator可以使用基于iview-admin的zywork-generator-ui（[https://github.com/gz-zywork/zywork-generator-ui](https://github.com/gz-zywork/zywork-generator-ui)）可视化的生成代码。

