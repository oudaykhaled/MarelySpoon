import android.app.Application
import com.ouday.marelyspoon.App
import com.ouday.marelyspoon.core.builder.ActivityBuilder
import com.ouday.marelyspoon.core.builder.FragmentBuilder
import com.ouday.marelyspoon.core.di.AppDatabaseModule
import com.ouday.marelyspoon.core.di.modules.ContextModule
import com.ouday.marelyspoon.core.di.modules.CoroutinesThreadsProvider
import com.ouday.marelyspoon.core.di.modules.NetworkModule
import com.ouday.marelyspoon.core.di.modules.SchedulersModule
import com.ouday.marelyspoon.main.di.RecipeDomainModule
import com.ouday.marelyspoon.main.di.RecipeLocalModule
import com.ouday.marelyspoon.main.di.RecipePresentationModule
import com.ouday.marelyspoon.main.di.RecipeRemoteModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        NetworkModule::class, ContextModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        SchedulersModule::class,
        AppDatabaseModule::class,
        CoroutinesThreadsProvider::class,
        RecipeRemoteModule::class,
        RecipeLocalModule::class,
        RecipeDomainModule::class,
        RecipePresentationModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}