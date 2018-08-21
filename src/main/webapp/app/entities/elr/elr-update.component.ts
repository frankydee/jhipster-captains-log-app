import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IELR } from 'app/shared/model/elr.model';
import { ELRService } from './elr.service';

@Component({
    selector: 'jhi-elr-update',
    templateUrl: './elr-update.component.html'
})
export class ELRUpdateComponent implements OnInit {
    private _eLR: IELR;
    isSaving: boolean;

    constructor(private eLRService: ELRService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eLR }) => {
            this.eLR = eLR;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eLR.id !== undefined) {
            this.subscribeToSaveResponse(this.eLRService.update(this.eLR));
        } else {
            this.subscribeToSaveResponse(this.eLRService.create(this.eLR));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IELR>>) {
        result.subscribe((res: HttpResponse<IELR>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get eLR() {
        return this._eLR;
    }

    set eLR(eLR: IELR) {
        this._eLR = eLR;
    }
}
