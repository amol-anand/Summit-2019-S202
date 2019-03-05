import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { SpaAngularEditableComponentsModule } from '@adobe/cq-angular-editable-components';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageComponent } from './components/page/page.component';
import { TextComponent } from './components/text/text.component';
import { ImageComponent } from './components/image/image.component';
import { HeaderComponent } from './components/header/header.component';
import { ListComponent } from './components/list/list.component';
import { EventlistComponent } from './components/eventlist/eventlist.component';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { EventfragmentComponent } from './components/eventfragment/eventfragment.component';
import { SpeakerlistComponent } from './components/speakerlist/speakerlist.component';


@NgModule({
  declarations: [
    AppComponent,
    PageComponent,
    TextComponent,
    ImageComponent,
    HeaderComponent,
    ListComponent,
    EventlistComponent,
    EventfragmentComponent,
    SpeakerlistComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SpaAngularEditableComponentsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [ImageComponent, ListComponent, TextComponent, EventlistComponent, EventfragmentComponent, SpeakerlistComponent]
})
export class AppModule { }
