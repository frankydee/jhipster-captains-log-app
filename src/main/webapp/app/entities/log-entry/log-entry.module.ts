import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaptainsLogAppSharedModule } from 'app/shared';
import {
    LogEntryComponent,
    LogEntryDetailComponent,
    LogEntryUpdateComponent,
    LogEntryDeletePopupComponent,
    LogEntryDeleteDialogComponent,
    logEntryRoute,
    logEntryPopupRoute
} from './';

const ENTITY_STATES = [...logEntryRoute, ...logEntryPopupRoute];

@NgModule({
    imports: [CaptainsLogAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LogEntryComponent,
        LogEntryDetailComponent,
        LogEntryUpdateComponent,
        LogEntryDeleteDialogComponent,
        LogEntryDeletePopupComponent
    ],
    entryComponents: [LogEntryComponent, LogEntryUpdateComponent, LogEntryDeleteDialogComponent, LogEntryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaptainsLogAppLogEntryModule {}
