import {asFunction, createContainer, InjectionMode} from "awilix";
import {AuthRepositoryImpl} from "../infrastructure/repositories/AuthRepositoryImpl.ts";
import {ApiClient} from "../infrastructure/datasources/api/ApiClient.ts";
import {LinksRepositoryImpl} from "../infrastructure/repositories/LinksRepositoryImpl.ts";
import {LinksApiDataSource} from "../infrastructure/datasources/api/LinksApiDataSource.ts";
import {LinksLocalDataSource} from "../infrastructure/datasources/local/LinksLocalDataSource.ts";
import {Database} from "../infrastructure/datasources/local/Database.ts";
import {LoginUseCaseImpl} from "../domain/usecases/auth/LoginUseCaseImpl.ts";
import {RegisterUseCaseImpl} from "../domain/usecases/auth/RegisterUseCaseImpl.ts";
import {AuthRepository} from "../domain/repositories/AuthRepository.ts";
import {CreateLinkUseCaseImpl} from "../domain/usecases/links/CreateLinkUseCaseImpl.ts";
import {LinksRepository} from "../domain/repositories/LinksRepository.ts";
import {GetLinksUseCaseImpl} from "../domain/usecases/links/GetLinksUseCaseImpl.ts";

const container = createContainer({
    injectionMode: InjectionMode.CLASSIC
})

container.register({
    // Client & db
    apiClient: asFunction(() => new ApiClient("http://127.0.0.1:8080")).singleton(),
    db: asFunction(() => new Database()).singleton(),

    // Repositories
    authRepository: asFunction(() => new AuthRepositoryImpl(
        container.resolve<ApiClient>("apiClient"),
    )).singleton(),

    linksRepository: asFunction(() => new LinksRepositoryImpl(
        new LinksApiDataSource(container.resolve<ApiClient>("apiClient")),
        new LinksLocalDataSource(container.resolve<Database>("db")),
    )).singleton(),

    // Usecases
    loginUseCase: asFunction(() => new LoginUseCaseImpl(
        container.resolve<AuthRepository>("authRepository"),
    )).singleton(),

    registerUseCase: asFunction(() => new RegisterUseCaseImpl(
        container.resolve<AuthRepository>("authRepository"),
    )).singleton(),

    getLinksUseCase: asFunction(() => new GetLinksUseCaseImpl(
        container.resolve<LinksRepository>("linksRepository"),
    )).singleton(),
    createLinkUseCase: asFunction(() => new CreateLinkUseCaseImpl(
        container.resolve<LinksRepository>("linksRepository"),
    )).singleton()
})

export default container;
