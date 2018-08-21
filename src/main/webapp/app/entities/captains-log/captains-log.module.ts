import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaptainsLogAppSharedModule } from 'app/shared';
import {
    CaptainsLogComponent,
    CaptainsLogDetailComponent,
    CaptainsLogUpdateComponent,
    CaptainsLogDeletePopupComponent,
    CaptainsLogDeleteDialogComponent,
    captainsLogRoute,
    captainsLogPopupRoute
} from './';

const ENTITY_STATES = [...captainsLogRoute, ...captainsLogPopupRoute];

@NgModule({
    imports: [CaptainsLogAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CaptainsLogComponent,
        CaptainsLogDetailComponent,
        CaptainsLogUpdateComponent,
        CaptainsLogDeleteDialogComponent,
        CaptainsLogDeletePopupComponent
    ],
    entryComponents: [CaptainsLogComponent, CaptainsLogUpdateComponent, CaptainsLogDeleteDialogComponent, CaptainsLogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaptainsLogAppCaptainsLogModule {}
