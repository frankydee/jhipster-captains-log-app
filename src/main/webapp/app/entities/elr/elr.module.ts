import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaptainsLogAppSharedModule } from 'app/shared';
import {
    ELRComponent,
    ELRDetailComponent,
    ELRUpdateComponent,
    ELRDeletePopupComponent,
    ELRDeleteDialogComponent,
    eLRRoute,
    eLRPopupRoute
} from './';

const ENTITY_STATES = [...eLRRoute, ...eLRPopupRoute];

@NgModule({
    imports: [CaptainsLogAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ELRComponent, ELRDetailComponent, ELRUpdateComponent, ELRDeleteDialogComponent, ELRDeletePopupComponent],
    entryComponents: [ELRComponent, ELRUpdateComponent, ELRDeleteDialogComponent, ELRDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaptainsLogAppELRModule {}
