import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CaptainsLogAppCaptainsLogModule } from './captains-log/captains-log.module';
import { CaptainsLogAppLogEntryModule } from './log-entry/log-entry.module';
import { CaptainsLogAppELRModule } from './elr/elr.module';
import { CaptainsLogAppTrackModule } from './track/track.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CaptainsLogAppCaptainsLogModule,
        CaptainsLogAppLogEntryModule,
        CaptainsLogAppELRModule,
        CaptainsLogAppTrackModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaptainsLogAppEntityModule {}
