import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICaptainsLog } from 'app/shared/model/captains-log.model';
import { CaptainsLogService } from './captains-log.service';

@Component({
    selector: 'jhi-captains-log-update',
    templateUrl: './captains-log-update.component.html'
})
export class CaptainsLogUpdateComponent implements OnInit {
    private _captainsLog: ICaptainsLog;
    isSaving: boolean;
    runDate: string;
    loadDate: string;

    constructor(private captainsLogService: CaptainsLogService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ captainsLog }) => {
            this.captainsLog = captainsLog;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.captainsLog.runDate = moment(this.runDate, DATE_TIME_FORMAT);
        this.captainsLog.loadDate = moment(this.loadDate, DATE_TIME_FORMAT);
        if (this.captainsLog.id !== undefined) {
            this.subscribeToSaveResponse(this.captainsLogService.update(this.captainsLog));
        } else {
            this.subscribeToSaveResponse(this.captainsLogService.create(this.captainsLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICaptainsLog>>) {
        result.subscribe((res: HttpResponse<ICaptainsLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get captainsLog() {
        return this._captainsLog;
    }

    set captainsLog(captainsLog: ICaptainsLog) {
        this._captainsLog = captainsLog;
        this.runDate = moment(captainsLog.runDate).format(DATE_TIME_FORMAT);
        this.loadDate = moment(captainsLog.loadDate).format(DATE_TIME_FORMAT);
    }
}
